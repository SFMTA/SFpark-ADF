package sfpark.rateChange.manager.view.backing.rateChange.helper;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dO.pmDistricts.PMDistrictsDO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.helper.PMDistrictAreaType;
import sfpark.adf.tools.model.provider.PMDistrictsProvider;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;

public class PickAreaTypeBean extends BaseBean implements PropertiesBeanInterface {

    private PMDistrictAreaType AreaType;
    private List<PMDistrictsDO> PMDistrictsDOs;

    private UISelectItems PMDistrictSelectItems;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickAreaTypeBean() {
        super();

        setAreaType(PMDistrictAreaType.PILOT);
        setPMDistrictsDOs(new ArrayList<PMDistrictsDO>());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL LIST VALUES

    public List<SelectItem> getListAreaType() {
        List<SelectItem> areaTypeList = new ArrayList<SelectItem>();

        for (PMDistrictAreaType areaType : PMDistrictAreaType.values()) {
            areaTypeList.add(new SelectItem(areaType,
                                            areaType.getStringForDisplay()));
        }

        return areaTypeList;
    }

    public List<SelectItem> getListPMDistricts() {
        return getListPMDistricts(getAreaType());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALIDATORS

    public void areaTypeValidator(FacesContext facesContext,
                                  UIComponent uiComponent, Object object) {
        //
        // Reuse as a value change listener for Area Type
        //
        setPMDistrictsDOs(new ArrayList<PMDistrictsDO>());

        PMDistrictAreaType areaType = (PMDistrictAreaType)object;

        getPMDistrictSelectItems().setValue(getListPMDistricts(areaType));
        addPartialTarget(getPMDistrictSelectItems());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void saveButtonHandler(ActionEvent event) {

        RateChangeHeaderDTO DTO =
            (RateChangeHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey());

        DTO.setAreaType(getAreaType());
        DTO.setPMDistrictDOs(getPMDistrictsDOs());

        setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                              DTO);

        moveOn();
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private List<SelectItem> getListPMDistricts(PMDistrictAreaType areaType) {
        List<SelectItem> pmDistrictList = new ArrayList<SelectItem>();

        for (PMDistrictsDO DO :
             PMDistrictsProvider.INSTANCE.getPMDistrictsFor(areaType)) {
            pmDistrictList.add(new SelectItem(DO, DO.getPMDistrictName()));
        }

        return pmDistrictList;
    }

    private void moveOn() {
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.AddRateChange.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setAreaType(PMDistrictAreaType AreaType) {
        this.AreaType = AreaType;
    }

    public PMDistrictAreaType getAreaType() {
        return AreaType;
    }

    public void setPMDistrictsDOs(List<PMDistrictsDO> PMDistrictsDOs) {
        this.PMDistrictsDOs = PMDistrictsDOs;
    }

    public List<PMDistrictsDO> getPMDistrictsDOs() {
        return PMDistrictsDOs;
    }

    public void setPMDistrictSelectItems(UISelectItems PMDistrictSelectItems) {
        this.PMDistrictSelectItems = PMDistrictSelectItems;
    }

    public UISelectItems getPMDistrictSelectItems() {
        return PMDistrictSelectItems;
    }
}
