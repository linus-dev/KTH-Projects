  # analyze.asm
  # This file written 2015 by F Lundevall
  # Copyright abandoned - this file is in the public domain.

	.text
main:
	li	$a0,0xFF
	andi $a0, $a0, 0xF
	li	$v0,11		# syscall with v0 = 11 will print out	
	syscall	
	nop			# delay slot filler (just in case)

stop:	j	stop		# loop forever here
	nop			# delay slot filler (just in case)

