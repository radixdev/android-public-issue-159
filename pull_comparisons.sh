#!/bin/sh

package=com.radix.kotlinlivingroom

folder_name=braze_heap_dumps

# delete any previous dumps
rm -rf $folder_name

# pull the heaps from the device
adb shell "run-as $package cp -vr ./cache/braze_heaps /sdcard/Download/$folder_name"
# adb shell "run-as $package rm -rf ./cache/braze_heaps"

# Pull those heaps off the SD card of th device
adb pull /sdcard/Download/$folder_name
# open $folder_name

# delete the temp folder on the SD card
adb shell "rm -rf /sdcard/Download/$folder_name"

# Convert all downloaded hprof files to a format MAT understands
# https://developer.android.com/studio/profile/memory-profiler#save-hprof

mkdir ./$folder_name/converted
cd $folder_name
find . -type f -name "*.hprof" -exec bash ../run_hprof_conv.sh {} \; 