package faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;

import common.CommonUtil;
import lombok.Data;
import lombok.ToString;

/**
 * ��ʂ̃t�H�[���l�Ɖ�ʑJ�ڃ��\�b�h���`.
 */
// ��b�X�R�[�v���w��
// ��b�X�R�[�v�̊J�n�E�I���^�C�~���O��pages.xml�Ŏw��
@Scope(ScopeType.CONVERSATION)
// @Name�A�m�e�[�V�����́AJSF��XHTML�t�@�C������#{inputFormAction}��
// Java�N���X���Q�Ƃł���悤�ɂ��Ă���(���o�b�L���O�r�[��)
// �������Aorg.jboss.seam.annotations.Name�N���X�Ȃ̂Œ���
@Name("inputFormAction")
@Data
@ToString(exclude={"birthMonthItems","birthDayItems","sexItems"})
public class InputFormAction implements Serializable {

    // �V���A���o�[�W����UID
    private static final long serialVersionUID = 7283339629129432007L;

    /** ���O */
    private String name;

    /** ���N����_�N */
    private String birthYear;

    /** ���N����_�� */
    private String birthMonth;

    /** ���N����_�� */
    private String birthDay;

    /** ���� */
    private String sex;

    /** ����(���x��) */
    private String sexLabel;

    /** ���� */
    private String memo;

    /** �m�F�`�F�b�N */
    private Boolean checked;

    /** ���N����_��(�I�����X�g) */
    private List<SelectItem> birthMonthItems;

    /** ���N����_��(�I�����X�g) */
    private List<SelectItem> birthDayItems;

    /** ����(�I�����X�g) */
    private List<SelectItem> sexItems;

    /**
     * �R���X�g���N�^�������ɑI�����X�g�̒l��ݒ�.
     */
    public InputFormAction(){
        // ���N����_��(�I�����X�g)
        birthMonthItems = new ArrayList<SelectItem>();
        birthMonthItems.add(new SelectItem("", ""));
        for(Integer i = 1; i <= 12; i++){
            birthMonthItems.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
        }

        // ���N����_��(�I�����X�g)
        birthDayItems = new ArrayList<SelectItem>();
        birthDayItems.add(new SelectItem("", ""));
        for(Integer i = 1; i <= 31; i++){
            birthDayItems.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
        }

        // ����(�I�����X�g)
        sexItems = new ArrayList<SelectItem>();
        sexItems.add(new SelectItem(String.valueOf(1),"�j"));
        sexItems.add(new SelectItem(String.valueOf(2),"��"));
    }

    /**
     * �m�F��ʂւ̑J��.
     * @return �m�F��ʂւ̃p�X
     */
    public String confirm(){
        // ���͉�ʂœ��̓`�F�b�N�G���[������ꍇ�́A��ʑJ�ڂ����G���[���b�Z�[�W��\��
        if(!this.validateInput()){
            return "chkerror";
        }

        // ����(���x��)��ݒ�
        if(!CommonUtil.isBlank(sex)){
            this.setSexLabel(this.getSexItems().get(Integer.parseInt(this.getSex())-1).getLabel());
        }
        // Form�ɐݒ肳�ꂽ�l���o��
        System.out.println(this.toString());

        // �m�F��ʂɑJ��
        return "confirm";
    }

    /**
     * ���͉�ʂɖ߂�.
     * @return ���͉�ʂւ̃p�X
     */
    public String back(){
        // ���͉�ʂɖ߂�
        return "back";
    }

    /**
     * ������ʂւ̑J��.
     * @return ������ʂւ̃p�X
     */
    public String send(){
        // �m�F��ʂɕ\�����ꂽ�l���o��
        System.out.println(this.toString());

        // ������ʂւ̑J��
        return "send";
    }
    
    /**
     * ���͍��ڂ̃`�F�b�N����
     * @return �`�F�b�N����
     */
    private boolean validateInput(){
        // �߂�l�Ƃ��ĕԂ��l
        boolean retVal = true;

        // FacesContext�AUIComponent���擾
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = context.getViewRoot().getChildren().get(1);

        // ���O���擾����
        UIInput nameUI = (UIInput)component.findComponent("name");
        String nameSt = (String)nameUI.getValue();

        // ���N�����̔N�E���E�����擾����
        UIInput birthYearUI = (UIInput)component.findComponent("birthYear");
        UIInput birthMonthUI = (UIInput)component.findComponent("birthMonth");
        UIInput birthDayUI = (UIInput)component.findComponent("birthDay");
        String birthYearSt = (String)birthYearUI.getValue();
        String birthMonthSt = (String)birthMonthUI.getValue();
        String birthDaySt = (String)birthDayUI.getValue();

        // ���ʂ��擾����
        UIInput sexUI = (UIInput)component.findComponent("sex");
        String sexSt = (String)sexUI.getValue();

        // ���͊m�F�`�F�b�N�̒l���擾����
        UIInput checkedUI = (UIInput)component.findComponent("checked");
        Boolean checkedBl = (Boolean)checkedUI.getValue();

        // ���O���󔒒l�̏ꍇ�̓G���[���b�Z�[�W��ݒ肷��
        if(CommonUtil.isBlank(nameSt)){
            addErrorMessage("object.empty.message", "���O");
            retVal = false;

        } else {
            // ���O��1�����ȏ�10�����ȉ��łȂ��ꍇ�̓G���[���b�Z�[�W��ݒ肷��
            if(!(nameSt.length() >= 1 && nameSt.length() <= 10)) {
                addErrorMessage("name.length.message", "���O", "1", "10");
                retVal = false;
            }
        }

        // ���N�����̔N�E���E�������ׂċ󔒒l�̏ꍇ�̓G���[���b�Z�[�W��ݒ肷��
        if (CommonUtil.isBlank(birthYearSt) && CommonUtil.isBlank(birthMonthSt)
                && CommonUtil.isBlank(birthDaySt)) {
            addErrorMessage("object.empty.message", "���N����");
            retVal = false;

        } else {
            // ���N���������݂��Ȃ����t�̏ꍇ�̓G���[���b�Z�[�W��Ԃ�
            String dateStr = birthYearSt + CommonUtil.addZero(birthMonthSt) + CommonUtil.addZero(birthDaySt);
            if(!CommonUtil.isCorrectDate(dateStr, "yyyyMMdd")){
                addErrorMessage("birthday.invalid.message");
                retVal = false;
            }
        }

        // ���ʂ��󔒒l�̏ꍇ�̓G���[���b�Z�[�W��ݒ肷��
        if(CommonUtil.isBlank(sexSt)){
            addErrorMessage("object.empty.message", "����");
            retVal = false;
        }
 
        // ���͊m�F�`�F�b�N�����w��̏ꍇ�̓G���[���b�Z�[�W��Ԃ�
        if(!checkedBl.booleanValue()){
            addErrorMessage("checked.notchecked.message");
            retVal = false;
        }

        return retVal;
    }

    /**
     * �����̃��b�Z�[�WKey�����G���[���b�Z�[�W��ǉ�.
     * @param messageKey ���b�Z�[�WKey
     * @param params ���b�Z�[�W�̖��ߍ��ݕ���
     */
    private void addErrorMessage(String messageKey, Object... params){
        FacesMessages facesMessages = (FacesMessages) Component.getInstance("facesMessages");
        facesMessages.addFromResourceBundle(Severity.ERROR, messageKey, params);
        FacesContext.getCurrentInstance().renderResponse();
    }
}
