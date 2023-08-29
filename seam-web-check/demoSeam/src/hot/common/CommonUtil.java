package common;

import java.text.SimpleDateFormat;

/**
 * ���ʃ��[�e�B���e�B�N���X.
 */
public class CommonUtil {

    /**
     * �����Ŏw�肵��������NULL,�󕶎�,�󔒂݂̂��ǂ������`�F�b�N����.
     * @param cs ������
     * @return ���茋��
     */
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * �����Ŏw�肵��������̒�����ԋp����.
     * @param cs ������
     * @return ������̒���
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * SimpleDateFormat�𗘗p���ē��t�`�F�b�N���s��.
     * @param dateStr �`�F�b�N�Ώە�����
     * @param dateFormat ���t�t�H�[�}�b�g
     * @return ���t�`�F�b�N����
     */
    public static boolean isCorrectDate(String dateStr, String dateFormat){
        if(CommonUtil.isBlank(dateStr) || CommonUtil.isBlank(dateFormat)){
            return false;
        }
        // JDK6�Ȃ̂ŁASimpleDateFormat�N���X�𗘗p���ē��t��ϊ�����
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            // �`�F�b�N�Ώە������Date�^�̓��t�ɕϊ��ł���΁A�`�F�b�NOK�Ƃ���
            sdf.parse(dateStr);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * ���l������1���̏ꍇ�A����0��t���ĕԂ�.
     * @param intNum ���l������
     * @return �ϊ��㐔�l������
     */
    public static String addZero(String intNum){
        if(CommonUtil.isBlank(intNum)){
            return intNum;
        }
        if(intNum.length() == 1){
            return "0" + intNum;
        }
        return intNum;
    }

}
