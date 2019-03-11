import bluetooth
import socket
import time
import RPi.GPIO as GPIO

tan = ["0" for i in range(15)]
check = ["0" for i in range(15)]
GPIO.setmode(GPIO.BCM)
GPIO.setup(23, GPIO.OUT)

for i in range(15):
	tan[i] = "e54b4562f170d3794abd16614d35acf1"
	print tan[i] + "; " + check[i]

	


def main():
	#ctx = SSL.Context(SSL.TLSv1_METHOD)
	#server_sock = SSL.Connection(ctx, bluetooth.BluetoothSocket(bluetooth.RFCOMM))
	#server_sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

	server_sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
	
	
	tanCounter = 0
	aid = "2"
	port = 19
	server_sock.bind(("",port))
	server_sock.listen(5)
	i = 0
	j = 0
	k = 0
		
	print "Listening to port %d" % port

	uuid = "8ce255c0-200a-11e0-ac64-0800200c9a66"
	bluetooth.advertise_service (server_sock, "Kaffeeautomat", uuid)

	while 1:
			client_sock,address = server_sock.accept()
			print "Accepted connection from ",address
	
			while 1:
				try:
					data = client_sock.recv(1024)
					print "received [%s]" % data
			
					if data.startswith("ACCEPT"):
						check[i] = data.split("T")[1]
						i += 1
						
						client_sock.send("OKSTOP")
						
					elif data.startswith("BUY"):
						print data
						tmp = data.split(":")
						print tmp[1] + "; " + tmp[2]
						j = i
						
						while j >= 0:
							print j
							if check[j] == tmp[1]:
								print "IN"
								if tan[j] == tmp[2]:
									check[j] = "done"
									tan[j] = "done"
									GPIO.output(23, GPIO.HIGH)
									time.sleep(5)
									GPIO.output(23, GPIO.LOW)
									print "Threw out"
									client_sock.send("OKSTOP") 
									j = 0
									break
								
								else:
									print "NOHIT"
								
							j -= 1
						
						if j == -1:
							client_sock.send("ERRORSTOP")
						j = 0
						
				except:
					print "Connection lost"
					break;
		
	client_sock.close()
	server_sock.close()

main()
