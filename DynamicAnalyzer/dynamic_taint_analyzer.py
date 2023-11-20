#!/usr/bin/python
# -*- coding: utf8 -*-
import linecache
import re
import sys

from examples.shell_example import execute_cmd

SINK_REGEX = re.compile(r"[ \t]+os\.system\((.*)\)")





class TaintAnalyzer:

    def __init__(self):
        # TODO: initialize the analyzer
        pass


    def mark_as_tainted(self, var):
        # TODO: mark a specific variable as tainted
        pass


    def generate_inputs(self):
        # TODO: generates inputs (hardcoded)

        # TODO: mark generated inputs as tainted
        pass


    def check_taint(self, frame, event, arg, code_line):
        # TODO: checks whether a tainted variable reached a sink!
        pass

    def run(self):
        # TODO: implement the tracer function
        def tracer(frame, event, arg):
            pass

        # TODO: generate inputs

        for tainted_input in self.inputs:
            # TODO: run the tracer for each input
            pass

        # TODO: return the vulnerable paths


if __name__ == '__main__':
    analyzer = TaintAnalyzer()
    vuln_paths = analyzer.run()
    if len(vuln_paths) > 0:
        print("\033[95m❌ Vulnerable:\033[00m")
        for i in range(len(vuln_paths)):
            print(f"\033[91m\tPath {i + 1}\033[00m")
            for line in vuln_paths[i]:
                print(f"\033[94m\t\t» {line}\033[00m")
    else:
        print("\033[92m✅ Not vulnerable\033[00m")