package sfpark.rateChange.manager.view.backing.rateChange.helper;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.model.RowKeySet;

import sfpark.adf.tools.model.data.dO.blocks.BlocksDO;
import sfpark.adf.tools.model.data.dO.pmDistricts.PMDistrictsDO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.provider.BlocksProvider;
import sfpark.adf.tools.model.provider.PMDistrictsProvider;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;

public class PickPMDistrictsAndBlocksBean extends BaseBean implements PropertiesBeanInterface {

    private List<PMDistrictsDO> PMDistrictsDOs;
    private RichTable BlockSelectionTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickPMDistrictsAndBlocksBean() {
        super();

        setPMDistrictsDOs(new ArrayList<PMDistrictsDO>());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL LIST VALUES

    public List<SelectItem> getListPMDistricts() {
        List<SelectItem> pmDistrictList = new ArrayList<SelectItem>();

        RateChangeHeaderDTO superDTO =
            (RateChangeHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey());

        for (PMDistrictsDO DO :
             PMDistrictsProvider.INSTANCE.getPMDistrictsFor(superDTO.getPMDistricts())) {

            pmDistrictList.add(new SelectItem(DO, DO.getPMDistrictName()));
        }

        return pmDistrictList;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void refreshButtonHandler(ActionEvent event) {
        getBlockSelectionTable().setValue(getListBlockSelection());
        addPartialTarget(getBlockSelectionTable());
    }

    public void saveButtonHandler(ActionEvent event) {

        List<PMDistrictsDO> pmDistrictDOs = getPMDistrictsDOs();

        if (pmDistrictDOs == null) {
            pmDistrictDOs = new ArrayList<PMDistrictsDO>();
        }

        List<BlocksDO> selectedBlockList = new ArrayList<BlocksDO>();

        if (!pmDistrictDOs.isEmpty()) {
            RichTable table = getBlockSelectionTable();
            RowKeySet rowKeySet = table.getSelectedRowKeys();
            int numOfSelectedRows = rowKeySet.size();

            List<Integer> pmDistrictIDList = new ArrayList<Integer>();

            for (PMDistrictsDO pmDistrictsDO : pmDistrictDOs) {
                pmDistrictIDList.add(new Integer(pmDistrictsDO.getPMDistrictID()));
            }

            if (numOfSelectedRows > 0) {
                for (Object rowKey : rowKeySet) {
                    table.setRowKey(rowKey);

                    BlocksDO DO = (BlocksDO)table.getRowData();

                    if (DO != null &&
                        pmDistrictIDList.contains(DO.getPMDistrictID())) {

                        selectedBlockList.add(DO);
                    }
                }
            }
        }

        RateChangeProcessControlDTO DTO =
            (RateChangeProcessControlDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey());

        DTO.setPMDistrictDOs(pmDistrictDOs);
        DTO.setBlockDOs(selectedBlockList);

        setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
                              DTO);

        moveOn();
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.DeployRateChange.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<BlocksDO> getListBlockSelection() {

        List<PMDistrictsDO> pmDistrictDOs = getPMDistrictsDOs();

        if (pmDistrictDOs == null || pmDistrictDOs.isEmpty()) {
            return new ArrayList<BlocksDO>();
        }

        List<String> tempList = new ArrayList<String>();

        for (PMDistrictsDO DO : pmDistrictDOs) {
            tempList.add(DO.getPMDistrictID());
        }

        return BlocksProvider.INSTANCE.getBlocksFor(StringUtil.convertListToString(tempList));
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setPMDistrictsDOs(List<PMDistrictsDO> PMDistrictsDOs) {
        this.PMDistrictsDOs = PMDistrictsDOs;
    }

    public List<PMDistrictsDO> getPMDistrictsDOs() {
        return PMDistrictsDOs;
    }

    public void setBlockSelectionTable(RichTable BlockSelectionTable) {
        this.BlockSelectionTable = BlockSelectionTable;
    }

    public RichTable getBlockSelectionTable() {
        return BlockSelectionTable;
    }
}
