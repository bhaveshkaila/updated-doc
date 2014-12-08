package com.wink.webservices.responsebean;

import java.util.ArrayList;
import java.util.List;

import com.wink.chat.ChatMessage;

/**
 * Created by bhavesh.kaila on 2/8/13.
 */
public class ChatHistoryResponse extends BaseResponse {

	public List<ChatMessage> messages = new ArrayList<ChatMessage>();

}
