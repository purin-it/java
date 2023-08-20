package org.domain.demoseam.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;

@Name("demoAction")
public class DemoAction
{
    @Logger private Log log;

    @In StatusMessages statusMessages;

    public void demoAction()
    {
        // implement your business logic here
        log.info("demoAction.demoAction() action called");
        statusMessages.add("demoAction");
    }

    // add additional action methods

}
