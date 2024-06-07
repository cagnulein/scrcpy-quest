// This file was @generated with LibOVRPlatform/codegen/main. Do not modify it!

#ifndef OVR_REQUESTS_COWATCHING_H
#define OVR_REQUESTS_COWATCHING_H

#include "OVR_Types.h"
#include "OVR_Platform_Defs.h"

#include "OVR_CowatchViewerArray.h"

/// Get the next page of entries
///
/// A message with type ::ovrMessage_Cowatching_GetNextCowatchViewerArrayPage will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// If no error occurred, the message will contain a payload of type ::ovrCowatchViewerArrayHandle.
/// Extract the payload from the message handle with ::ovr_Message_GetCowatchViewerArray().
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_GetNextCowatchViewerArrayPage(ovrCowatchViewerArrayHandle handle);

/// Presenter data is used to drive a cowatching session. This can be called
/// when there is an active cowatching session.
///
/// A message with type ::ovrMessage_Cowatching_GetPresenterData will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// If no error occurred, the message will contain a payload of type const char *.
/// Extract the payload from the message handle with ::ovr_Message_GetString().
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_GetPresenterData();

/// Get the viewer data of everyone who is in a cowatching session whose data
/// was set by ovr_Cowatching_SetViewerData(). This can be called when there is
/// an active cowatching session.
///
/// A message with type ::ovrMessage_Cowatching_GetViewersData will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// If no error occurred, the message will contain a payload of type ::ovrCowatchViewerArrayHandle.
/// Extract the payload from the message handle with ::ovr_Message_GetCowatchViewerArray().
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_GetViewersData();

/// Check whether the current user is in the current cowatching session.
///
/// A message with type ::ovrMessage_Cowatching_IsInSession will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// If no error occurred, the message will contain a payload of type ::ovrCowatchingStateHandle.
/// Extract the payload from the message handle with ::ovr_Message_GetCowatchingState().
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_IsInSession();

/// Join the current cowatching session. Viewer data can only be updated by
/// users who are in the session.
///
/// A message with type ::ovrMessage_Cowatching_JoinSession will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// This response has no payload. If no error occurred, the request was successful. Yay!
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_JoinSession();

/// Launch a dialog for inviting users to cowatch in Copresent Home.
///
/// A message with type ::ovrMessage_Cowatching_LaunchInviteDialog will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// This response has no payload. If no error occurred, the request was successful. Yay!
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_LaunchInviteDialog();

/// Leave the current cowatching session. Viewer data will no longer be
/// relevant.
///
/// A message with type ::ovrMessage_Cowatching_LeaveSession will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// This response has no payload. If no error occurred, the request was successful. Yay!
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_LeaveSession();

/// Request to start a cowatching session as the presenter while copresent in
/// home.
///
/// A message with type ::ovrMessage_Cowatching_RequestToPresent will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// This response has no payload. If no error occurred, the request was successful. Yay!
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_RequestToPresent();

/// Stop being the presenter. This will end the cowatching session.
///
/// A message with type ::ovrMessage_Cowatching_ResignFromPresenting will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// This response has no payload. If no error occurred, the request was successful. Yay!
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_ResignFromPresenting();

/// Set the data that drives a cowatching session. This method is only callable
/// by the presenter. Video title cannot exceed 100 characters, and data size
/// is limited to 500 characters. The data will be eventually consistent across
/// all users.
///
/// A message with type ::ovrMessage_Cowatching_SetPresenterData will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// This response has no payload. If no error occurred, the request was successful. Yay!
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_SetPresenterData(const char *video_title, const char *presenter_data);

/// Set the current user's viewer data to be shared with copresent users. This
/// can be called when there is an active cowatching session. Data size is
/// limited to 500 characters. The data will be eventually consistent across
/// all users.
///
/// A message with type ::ovrMessage_Cowatching_SetViewerData will be generated in response.
///
/// First call ::ovr_Message_IsError() to check if an error occurred.
///
/// This response has no payload. If no error occurred, the request was successful. Yay!
OVRP_PUBLIC_FUNCTION(ovrRequest) ovr_Cowatching_SetViewerData(const char *viewer_data);

#endif
