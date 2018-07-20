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

