package faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import common.CommonUtil;
import lombok.Data;
import lombok.ToString;

/**
 * ��ʂ̃t�H�[���l�Ɖ�ʑJ�ڃ��\�b�h���`.
 */
// ��b�X�R�[�v���w��
// ��b�X�R�[�v�̊J�n�E�I���^�C�~���O��pages.xml�Ŏw��
@Scope(ScopeType.CONVERSATION)
//�@@Name�A�m�e�[�V�����́AJSF��XHTML�t�@�C������#{inputFormAction}��
//�@Java�N���X���Q�Ƃł���悤�ɂ��Ă���(���o�b�L���O�r�[��)
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

}
