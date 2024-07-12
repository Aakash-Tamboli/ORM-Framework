#!/bin/bash

rm -r ../classes/com
javac -d ../classes com/thinking/machines/orm/annotation/*.java
javac -d ../classes com/thinking/machines/orm/exception/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/util/string/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/util/constant/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/util/foreignkey/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/util/json/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/util/table/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/util/column/*.java


