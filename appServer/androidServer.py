import socket
import threading
import random
import time

def handleConnection(clientSocket):
	direction = ['sim','up','down','left','right']
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

	startServer("",4443)