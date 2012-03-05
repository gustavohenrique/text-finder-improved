package br.com.wdev.action;

import hudson.model.Action;

import java.io.File;

import br.com.wdev.helpers.Finder;
import br.com.wdev.util.XmlParserUtil;

public class FinderAction implements Action {
    
    private Finder finder;
    
    private String xml;
    
    public FinderAction(File workspace, Finder finder) {
        XmlParserUtil xmlParserUtil = new XmlParserUtil();
        xml = xmlParserUtil.toXml(finder);
        xmlParserUtil.saveFile(new File(workspace.getAbsolutePath() + workspace.separator + "text-finder-improved.xml"), xml);
    }
    
    public String getRegexp() {
        
        return "ddsfdsfs";
    }

    public String getIconFileName() {
        return "/plugin/text-finder-improved/images/icon.png";
    }   

    public String getDisplayName() {
        return "Text Finder";
    }

    public String getUrlName() {
        return "finderaction";
    }

}
