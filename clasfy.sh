#!/bin/bash
#jubaclassifier --configpath clasfy.json --timeout 400 --interconnect_timeout 400 --interval_sec 8 --thread 10  --rpc-port $1& 
jubaclassifier --configpath clasfy.json --thread 8  --rpc-port $1& 
