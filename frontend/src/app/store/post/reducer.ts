import { createReducer, on } from '@ngrx/store';
import { PostAcions } from './actions';
import { initialState, PostState } from './post.state';
import { Message } from 'src/app/models/message.model';
import { NotificationModel } from 'src/app/models/notification.model';
import { PostInitMessage } from '../payloads/post-init-messages.model';

export const postReducer = createReducer(
  initialState,
  on(PostAcions.SetNewPostAction, setNewPostGroup),
  on(PostAcions.InitMessagesAction, initMessages),
  on(PostAcions.ResetPostsAction, resetPosts),
);

function initMessages(state: PostState, payload: PostInitMessage): PostState {
   const newSate = {...state};
   if(payload.groupUuid){
    newSate.generalMessages = [];
    newSate.selectedGroupMessages = payload.messages;
   } else {
    newSate.generalMessages = payload.messages;
    newSate.selectedGroupMessages = [];
   }
  
  return newSate;
}

function setNewPostGroup(state: PostState, message: Message): PostState {
    const selectedGroupMessages = [...state.selectedGroupMessages];
    const generalMessages = [...state.generalMessages];
    const notifications = [...state.notifications];

    const notification: NotificationModel = {
      label: '',
      content: '',
      timestamp: message.timestamp,
      isOpenned: false,
    };

    if(notifications.length){
      notifications.unshift(notification);
    } else {
      notifications.push(notification);
    }

    if(message.group && message.group.uuid){
      if(selectedGroupMessages?.length){
        selectedGroupMessages.unshift(message);
      } else {
        selectedGroupMessages.push(message);
      }
      
      if(selectedGroupMessages.length>10){
        selectedGroupMessages.pop();
      }
    } else {
      if(generalMessages?.length){
        generalMessages.unshift(message);
      } else {
        generalMessages.push(message);
      }
      if(generalMessages.length>10){
        generalMessages.pop();
      }
    }

    return {
      selectedGroupMessages,
      generalMessages,
      notifications,
    };
}

function resetPosts(_: PostState): PostState {
  return initialState;
}