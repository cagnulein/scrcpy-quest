// This file was @generated with LibOVRPlatform/codegen/main. Do not modify it!

#ifndef OVR_COWATCHVIEWERUPDATE_H
#define OVR_COWATCHVIEWERUPDATE_H

#include "OVR_Platform_Defs.h"
#include "OVR_CowatchViewerArray.h"
#include "OVR_Types.h"

typedef struct ovrCowatchViewerUpdate *ovrCowatchViewerUpdateHandle;

/// List of viewer data of all cowatch participants.
OVRP_PUBLIC_FUNCTION(ovrCowatchViewerArrayHandle) ovr_CowatchViewerUpdate_GetDataList(const ovrCowatchViewerUpdateHandle obj);

/// User ID of the user with updated viewer data.
OVRP_PUBLIC_FUNCTION(ovrID) ovr_CowatchViewerUpdate_GetId(const ovrCowatchViewerUpdateHandle obj);


#endif
