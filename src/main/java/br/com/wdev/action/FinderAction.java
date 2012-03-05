package br.com.wdev.action;

import java.io.File;

import br.com.wdev.helpers.Finder;
import br.com.wdev.util.XmlParserUtil;
import hudson.model.Action;

public class FinderAction implements Action {
    
    public FinderAction(File workspace, Finder finder) {
        XmlParserUtil xmlParserUtil = new XmlParserUtil();
        xmlParserUtil.toXml(finder, new File(workspace.getAbsolutePath() + workspace.separator + "text-finder-improved.xml"));
    }

    public String getIconFileName() {
        return "";
    }

    public String getDisplayName() {
        return "text-finder-improved";
    }

    public String getUrlName() {
        return "url";
    }

}
