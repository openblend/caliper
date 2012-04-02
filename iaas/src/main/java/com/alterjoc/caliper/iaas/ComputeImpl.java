package com.alterjoc.caliper.iaas;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.ec2.compute.options.EC2TemplateOptions;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

/**
 * @author Matej Lazar
 */
public class ComputeImpl implements Compute {

    private IaasProperties iaasProperties;
    private ComputeService service;
    private List<NodeMetadata> runningInstances = new ArrayList<NodeMetadata>();

    public ComputeImpl(IaasProperties iaasProperties) {

        this.iaasProperties = iaasProperties;

        Properties overrides = new Properties();
        overrides.setProperty("eucalyptus.endpoint", iaasProperties.getProviderUrl());

        ComputeServiceContext  context = new ComputeServiceContextFactory().createContext(
                iaasProperties.getProvider(),
                iaasProperties.getUserName(),
                iaasProperties.getPassword(),
                ImmutableSet.<Module> of(),
                overrides);
        service = context.getComputeService();
    }

    public String createInstance() throws RunNodesException {
        String keyPairName = iaasProperties.getKeyPairName();

        String imageId = iaasProperties.getImageId();
        Template template = service.templateBuilder().imageId(imageId).minRam(384).build();

        template.getOptions().as(EC2TemplateOptions.class).securityGroups(iaasProperties.getSecurityGroups());
        template.getOptions().as(EC2TemplateOptions.class).keyPair(keyPairName);

        String group = iaasProperties.getGroup();
        Set<? extends NodeMetadata> nodes = service.createNodesInGroup(group, 1, template);
        NodeMetadata node;
        try {
            //get first as we always create only one
            node = nodes.iterator().next();
        } catch (NoSuchElementException e) {
            throw new RunNodesException(group, 1, template, null, null, null);
        }
        runningInstances.add(node);
        return node.getId();
    }

    public String scaleUp() throws RunNodesException {
        return createInstance();
    }

    public void scaleDown() throws Exception {
        NodeMetadata instance = getLastInstance();
        destroy(instance);
    }

    public void terminate() {
        for (NodeMetadata instance : runningInstances) {
            destroy(instance);
        }
    }

    private void destroy(NodeMetadata instance) {
        service.destroyNode(instance.getId());
        runningInstances.remove(instance);
    }

    private NodeMetadata getLastInstance() throws Exception {
        int lastIndex = runningInstances.size() - 1;
        if (lastIndex < 0) {
            throw new Exception("No more instances.");
        }
        return runningInstances.get(lastIndex );
    }

}
