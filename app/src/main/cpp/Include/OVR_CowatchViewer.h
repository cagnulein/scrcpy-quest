// This file was @generated with LibOVRPlatform/codegen/main. Do not modify it!

#ifndef OVR_COWATCHVIEWER_H
#define OVR_COWATCHVIEWER_H

#include "OVR_Platform_Defs.h"
#include "OVR_Types.h"

typedef struct ovrCowatchViewer *ovrCowatchViewerHandle;

/// Viewer data set by this cowatching viewer.
OVRP_PUBLIC_FUNCTION(const char *) ovr_CowatchViewer_GetData(const ovrCowatchViewerHandle obj);

/// User ID of the owner of data.
OVRP_PUBLIC_FUNCTION(ovrID) ovr_CowatchViewer_GetId(const ovrCowatchViewerHandle obj);


#endif
