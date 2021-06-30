package com.zjc.formework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * @ClassName : ZViewResolver
 * @Author : zhangjiacheng
 * @Date : 2021/1/14
 * @Description : TODO
 */
public class ZViewResolver {

    private File templateRootDir;

    private final String DEFAULT_TEMPLATE_SUFFX = ".html";

    public ZViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    public ZView resolveViewName(String viewName, Locale locale) throws Exception{
        if (null == viewName || "".equals(viewName.trim())){
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));

        return new ZView(templateFile);
    }

}
