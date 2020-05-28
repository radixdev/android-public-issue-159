#!/bin/sh

file=$1
# echo "Using file $file"
# if the package is null, JFI
if [ -z $file ]
then
  echo "File name is null. It's the first argument"
  exit 1
fi

basename=$(basename $file)
# echo $basename

hprof-conv $basename converted/$basename