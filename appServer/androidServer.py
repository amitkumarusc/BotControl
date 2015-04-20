import socket
import threading
import random
import time
"""import Adafruit_BBIO.GPIO as GPIO

def initialiseRobot():
	GPIO.setup(upPin1,GPIO.OUT)
	GPIO.setup(upPin2,GPIO.OUT)
	GPIO.setup(downPin1,GPIO.OUT)
	GPIO.setup(downPin2,GPIO.OUT)
	GPIO.setup(leftPin1,GPIO.OUT)
	GPIO.setup(leftPin2,GPIO.OUT)
	GPIO.setup(rightPin1,GPIO.OUT)
	GPIO.setup(rightPin2,GPIO.OUT)

def moveUp():
	global upPin1,upPin2
	GPIO.output(upPin1,GPIO.HIGH)
	GPIO.output(upPin2,GPIO.LOW)

def moveDown():
	global downPin1,downPin2
	GPIO.output(downPin1,GPIO.HIGH)
	GPIO.output(downPin2,GPIO.LOW)

def moveLeft():
	global leftPin1,leftPin2
	GPIO.output(leftPin1,GPIO.HIGH)
	GPIO.output(leftPin2,GPIO.LOW)

def moveRight():
	global rightPin1,rightPin2
	GPIO.output(rightPin1,GPIO.HIGH)
	GPIO.output(rightPin2,GPIO.LOW)"""


def handleConnection(clientSocket):
	direction = ['sim','up','down','left','right','stop']
	data = ""
	print "Socket name: ",clientSocket.getsockname()
	try:
		while True:
			data = clientSocket.recv(2).decode()
			data = int(ord(data))
			print direction[data]
			if not data:
				print "Break"
				break
	except:
		print "Client Disconnected"
		clientSocket.shutdown(socket.SHUT_RDWR)
		clientSocket.close()


def startServer(ip,port):
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	sock.bind((ip,port))
	sock.listen(5)
	print "Server started on: ",port,"\n"
	try:
		while True:
			newSock, address = sock.accept()
			#print "Amit ",newSock.shape
			print "\n",address," connected"
			print newSock
			handler = threading.Thread(target=handleConnection,args=(newSock,))
			handler.start()
	except:
		sock.shutdown(socket.SHUT_RDWR)
		sock.close()


if __name__ == "__main__":
	#initialiseRobot()
	startServer("",4445)
