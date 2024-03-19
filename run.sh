#!/bin/bash

options=(
    "-v -i 5 -s 1"
    "-v -i 7 -s 2"
    "-v -i 10 -s 4"
    "-v -i 15 -s 7"
)

base_command() {
    /home/karlenko/.jdks/openjdk-21.0.1/bin/java -javaagent:/snap/intellij-idea-ultimate/486/lib/idea_rt.jar=34963:/snap/intellij-idea-ultimate/486/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /home/karlenko/IdeaProjects/lab3/out/production/lab3:/home/karlenko/.m2/repository/com/anaptecs/jeaf/owalibs/org.apache.commons.cli/4.3.1/org.apache.commons.cli-4.3.1.jar Main "$@"
}

print_verbose_results() {
    for i in "${options[@]}"; 
    do
        time base_command ${i}
        echo "==================================="
    done
}

print_verbose_results
