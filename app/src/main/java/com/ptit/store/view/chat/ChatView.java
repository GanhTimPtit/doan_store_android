package com.ptit.store.view.chat;


import com.ptit.store.models.response.PageList;
import com.ptit.store.models.model_chat.Message;
import com.ptit.store.models.model_chat.UserMessage;

public interface ChatView {
    void addMessage(UserMessage userMessag);
    void modifiedMessage(UserMessage userMessage);
    void onMessageSeen();


    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void refreshMessages(PageList<Message> messagePageList);
}
