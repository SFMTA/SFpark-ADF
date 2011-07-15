package sfpark.asset.manager.application.listener;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import javax.servlet.http.HttpServletResponse;

public class CacheControlListener implements PhaseListener {
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    public void afterPhase(PhaseEvent phaseEvent) {
    }

    public void beforePhase(PhaseEvent phaseEvent) {
        FacesContext facesContext = phaseEvent.getFacesContext();

        Date now = new Date();

        HttpServletResponse response =
            (HttpServletResponse)facesContext.getExternalContext().getResponse();
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
        response.addHeader("Expires", now.toString());
    }
}
