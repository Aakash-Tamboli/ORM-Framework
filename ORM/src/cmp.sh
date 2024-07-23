#!/bin/bash

rm -r ../classes/com

javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/model/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/pojo/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/column/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/string/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/constant/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/foreignkey/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/json/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/compiler/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/jar/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/table/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/validator/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/sql/statement/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/sql/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/util/fieldwrapper/*.java
javac -d ../classes com/thinking/machines/orm/annotation/*.java
javac -d ../classes com/thinking/machines/orm/exception/*.java
javac -classpath ../dependencies/*:. -d ../classes com/thinking/machines/orm/*.java
