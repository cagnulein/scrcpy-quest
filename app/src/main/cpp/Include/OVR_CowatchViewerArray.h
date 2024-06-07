// This file was @generated with LibOVRPlatform/codegen/main. Do not modify it!

#ifndef OVR_COWATCHVIEWERARRAY_H
#define OVR_COWATCHVIEWERARRAY_H

#include "OVR_Platform_Defs.h"
#include "OVR_CowatchViewer.h"
#include <stdbool.h>
#include <stddef.h>

typedef struct ovrCowatchViewerArray *ovrCowatchViewerArrayHandle;

OVRP_PUBLIC_FUNCTION(ovrCowatchViewerHandle) ovr_CowatchViewerArray_GetElement(const ovrCowatchViewerArrayHandle obj, size_t index);
OVRP_PUBLIC_FUNCTION(const char *)           ovr_CowatchViewerArray_GetNextUrl(const ovrCowatchViewerArrayHandle obj);
OVRP_PUBLIC_FUNCTION(size_t)                 ovr_CowatchViewerArray_GetSize(const ovrCowatchViewerArrayHandle obj);
OVRP_PUBLIC_FUNCTION(bool)                   ovr_CowatchViewerArray_HasNextPage(const ovrCowatchViewerArrayHandle obj);

#endif
