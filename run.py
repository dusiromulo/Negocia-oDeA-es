#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys
import os
import platform


if platform.system() == "Windows":
	def generate_jar():
		os.system('jar cf build\\StockLogger.jar -C StockLogger/bin/ stockLogger')
		os.system('jar cf build\\Client.jar -C Client\\bin\\ client')

	def run_server():
		os.system('jar cf build\\StockSeller.jar -C StockSeller\\bin\\ stockSeller')
		os.system('java -Djava.endorsed.dirs=lib  -cp "build\\StockSeller.jar;build\\;lib\\*" stockSeller.Main build\\stockServer build\\stockExchange')

	def run_logger():
		os.system('jar cf build\\StockLogger.jar -C StockLogger\\bin\\ stockLogger')
		os.system('java -Djava.endorsed.dirs=lib  -cp "build\\StockLogger.jar;build\\;lib\\*" stockLogger.Main build\\stockExchange')

	def run_client():
		os.system('jar cf build\\Client.jar -C Client\\bin\\ client')
		os.system('java -Djava.endorsed.dirs=lib  -cp "build\\Client.jar;build\\;lib\\*" client.Main build\\stockServer build\\stockExchange')

	def run_idls():
		os.system("ant -f compileIDL.xml generate_idl")
		os.system("ant -f compileJavaWindows.xml javac")

elif platform.system() == "Linux":
	def run_server():
		os.system('jar cf build/StockSeller.jar -C StockSeller/bin/ stockSeller')
		os.system('java -Djava.endorsed.dirs=lib  -cp "build/StockSeller.jar:build/:lib/*" stockSeller.Main build/stockServer build/stockExchange')

	def run_logger():
		os.system('jar cf build/StockLogger.jar -C StockLogger/bin/ stockLogger')
		os.system('java -Djava.endorsed.dirs=lib -cp "build/StockLogger.jar:build/:lib/*" stockLogger.Main build/stockExchange')

	def run_client():
		os.system('jar cf build/Client.jar -C Client/bin/ client')
		os.system('java -Djava.endorsed.dirs=lib -cp "build/Client.jar:build/:lib/*" client.Main build/stockServer build/stockExchange')

	def run_idls():
		os.system("ant -f compileIDL.xml generate_idl")
		os.system("ant -f compileJavaLinux.xml javac")

	def clear_build():
		os.system("rm -r build/")
		os.system("mkdir build")


cmdargs = sys.argv
if(len(cmdargs) > 1):
	if(cmdargs[1] == "run_server"):
		run_server()
	elif(cmdargs[1] == "run_logger"):
		run_logger()
	elif(cmdargs[1] == "run_client"):
		run_client()
	elif(cmdargs[1] == "run_idls"):
		run_idls()
	elif(cmdargs[1] == "clear"):
		clear_build()
	else:
		print "Comando não encontrado."