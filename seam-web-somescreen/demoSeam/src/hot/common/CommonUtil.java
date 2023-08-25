package common;

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
}
