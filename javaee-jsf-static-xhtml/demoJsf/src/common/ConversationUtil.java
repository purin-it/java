package common;

import javax.enterprise.context.Conversation;

/**
 * 会話スコープを開始・終了するクラス.
 */
public class ConversationUtil {

    /**
     * 会話スコープを開始.
     * @param conv 会話スコープマネージャー
     */
    public static void beginConv(Conversation conv){
        // 会話スコープを開始していない場合は開始する
        if(conv.isTransient()){
            conv.begin();
        }
    }

    /**
     * 会話スコープを終了.
     * @param conv 会話スコープマネージャー
     */
    public static void endConv(Conversation conv){
        // 会話スコープを終了していない場合は終了する
        if(!conv.isTransient()){
            conv.end();
        }
    }

}
