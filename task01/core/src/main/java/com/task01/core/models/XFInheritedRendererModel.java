package com.task01.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


@Model(adaptables = Resource.class,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class XFInheritedRendererModel {

    @Inject
    private Resource resource;

    @Inject
    private ResourceResolver resourceResolver;

    private Resource xfResource;

    @PostConstruct
    protected void init() {
        Page currentPage = resourceResolver.adaptTo(PageManager.class).getContainingPage(resource);
        while (currentPage != null) {
            String xfPath = currentPage.getProperties().get("xfPath", String.class);
            if (xfPath != null && resourceResolver.getResource(xfPath) != null) {
                xfResource = resourceResolver.getResource(xfPath);
                break;
            }
            currentPage = currentPage.getParent();
        }
    }

    public Resource getXfResource() {
        return xfResource;
    }
}
