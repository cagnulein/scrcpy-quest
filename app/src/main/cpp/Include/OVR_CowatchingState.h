// This file was @generated with LibOVRPlatform/codegen/main. Do not modify it!

#ifndef OVR_COWATCHINGSTATE_H
#define OVR_COWATCHINGSTATE_H

#include "OVR_Platform_Defs.h"
#include <stdbool.h>

typedef struct ovrCowatchingState *ovrCowatchingStateHandle;

/// Indicates if the current user is in a cowatching session.
OVRP_PUBLIC_FUNCTION(bool) ovr_CowatchingState_GetInSession(const ovrCowatchingStateHandle obj);


#endif
