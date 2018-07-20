# How to monitor

... the commands to the several endpoints

## Know your endpoints

The following command allows you to investigate where your endpoints are:

```
/opt/redislabs/bin/rladmin status
```

```
CLUSTER NODES:
NODE:ID  ROLE    ADDRESS      EXTERNAL_ADDRESS    HOSTNAME       SHARDS  CORES        RAM                AVAILABLE_RAM      VERSION   STATUS 
*node:1  master  172.17.0.3   172.17.0.3          fd30e77bf0bf   1/100   8            25.45GB/31.44GB    18.67GB/25.15GB    5.2.0-14  OK     
node:2   slave   172.17.0.5   172.17.0.5          69bde89776f5   1/100   8            25.45GB/31.44GB    18.67GB/25.15GB    5.2.0-14  OK     
node:3   slave   172.17.0.4   172.17.0.4          8b18d00808ad   0/100   8            25.45GB/31.44GB    19.17GB/25.15GB    5.2.0-14  OK     

DATABASES:
DB:ID NAME TYPE  STATUS SHARDS PLACEMENT REPLICATION PERSISTENCE ENDPOINT                                                                                        
db:2  demo redis active 2      sparse    disabled    disabled    redis-16379.internal.cluster.ubuntu-docker.org:16379/redis-16379.cluster.ubuntu-docker.org:16379

ENDPOINTS:
DB:ID           NAME         ID                                 NODE              ROLE                                            SSL        
db:2            demo         endpoint:2:1                       node:1            all-master-shards                               No         
db:2            demo         endpoint:2:1                       node:2            all-master-shards                               No         

SHARDS:
DB:ID        NAME            ID                NODE           ROLE           SLOTS                   USED_MEMORY                STATUS       
db:2         demo            redis:2           node:1         master         0-8191                  2.58MB                     OK           
db:2         demo            redis:3           node:2         master         8192-16383              2.58MB                     OK           
```

You can see that node 1 (172.17.0.3) and node 2 (172.17.0.5) are having a proxy running.

The command `redis-cli -h 172.17.0.3 -p 16379 CLUSTER NODES` is confirming it:

```
1 172.17.0.3:16379 myself,master - 0 0 0 connected 0-8191
2 172.17.0.5:16379 master - 0 0 0 connected 8192-16383
```


### Connect and monitor

Just use the redis-cli in order to monitor what's happening on the shards behind both endpoints:

```
redis-cli -h 172.17.0.3 -p 16379 MONITOR >> node1.log &
redis-cli -h 172.17.0.5 -p 16379 MONITOR >> node2.log &
```

Here the first lines of 'node1.log':

```
1532115585.343345 [0 172.17.0.3:53716] "CLIENT" "SETNAME" "lettuce#ClusterTopologyRefresh"
1532115585.351345 [0 172.17.0.3:53716] "CLUSTER" "NODES"
1532115585.351345 [0 172.17.0.3:53716] "CLIENT" "LIST"
1532115585.511339 [0 172.17.0.3:53722] "COMMAND"
1532115585.539338 [0 172.17.0.3:53724] "SET" "hello:0" "world:0"
1532115585.543338 [0 172.17.0.3:53724] "SET" "hello:1" "world:1"
1532115585.563337 [0 172.17.0.3:53724] "SET" "hello:4" "world:4"
1532115585.567337 [0 172.17.0.3:53724] "SET" "hello:5" "world:5"
1532115585.571337 [0 172.17.0.3:53724] "SET" "hello:8" "world:8"
1532115585.571337 [0 172.17.0.3:53724] "SET" "hello:9" "world:9"
```

And the same for 'node2.log':

```
1532115585.399343 [0 172.17.0.3:38774] "CLIENT" "SETNAME" "lettuce#ClusterTopologyRefresh"
1532115585.399343 [0 172.17.0.3:38774] "CLUSTER" "NODES"
1532115585.399343 [0 172.17.0.3:38774] "CLIENT" "LIST"
1532115585.559337 [0 172.17.0.3:38792] "SET" "hello:2" "world:2"
1532115585.563337 [0 172.17.0.3:38792] "SET" "hello:3" "world:3"
1532115585.567337 [0 172.17.0.3:38792] "SET" "hello:6" "world:6"
1532115585.567337 [0 172.17.0.3:38792] "SET" "hello:7" "world:7"
1532115585.575336 [0 172.17.0.3:38792] "SET" "hello:12" "world:12"
1532115585.579336 [0 172.17.0.3:38792] "SET" "hello:13" "world:13"
1532115585.591336 [0 172.17.0.3:38792] "SET" "hello:16" "world:16"
```
