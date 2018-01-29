  # timetemplate.asm
  # Written 2015 by F Lundevall
  # Copyright abandonded - this file is in the public domain.

.macro	PUSH (%reg)
	addi	$sp,$sp,-4
	sw	%reg,0($sp)
.end_macro

.macro	POP (%reg)
	lw	%reg,0($sp)
	addi	$sp,$sp,4
.end_macro

	.data
	.align 2
mytime:	.word 0x5957
timstr:	.ascii "  :  \0"
	.text
main:
	# print timstr
	la	$a0,timstr
	li	$v0,4
	syscall
	nop
	# wait a little
	li	$a0,1000
	jal	delay
	nop
	# call tick
	la	$a0,mytime
	jal	tick
	nop
	# call your function time2string
	la	$a0,timstr
	la	$t0,mytime
	lw	$a1,0($t0)
	jal	time2string
	nop
	# print a newline
	li	$a0,10
	li	$v0,11
	syscall
	nop
	# go back and do it all again
	j	main
	nop
# tick: update time pointed to by $a0
tick:	lw	$t0,0($a0)	# get time
	addiu	$t0,$t0,1	# increase
	andi	$t1,$t0,0xf	# check lowest digit
	sltiu	$t2,$t1,0xa	# if digit < a, okay
	bnez	$t2,tiend
	nop
	addiu	$t0,$t0,0x6	# adjust lowest digit
	andi	$t1,$t0,0xf0	# check next digit
	sltiu	$t2,$t1,0x60	# if digit < 6, okay
	bnez	$t2,tiend
	nop
	addiu	$t0,$t0,0xa0	# adjust digit
	andi	$t1,$t0,0xf00	# check minute digit
	sltiu	$t2,$t1,0xa00	# if digit < a, okay
	bnez	$t2,tiend
	nop
	addiu	$t0,$t0,0x600	# adjust digit
	andi	$t1,$t0,0xf000	# check last digit
	sltiu	$t2,$t1,0x6000	# if digit < 6, okay
	bnez	$t2,tiend
	nop
	addiu	$t0,$t0,0xa000	# adjust last digit
tiend:	sw	$t0,0($a0)	# save updated result
	jr	$ra		# return
	nop

  # you can write your code for subroutine "hexasc" below this line
  #
delay:
  PUSH $a0
  loop_outer:
    addi $t0, $zero, 6
    loop_inner:
      subi $t0, $t0, 1
      bne $t0, 0, loop_inner
      nop
    subi $a0, $a0, 1
    bne $a0, 0, loop_outer
    nop
  POP $a0
  jr $ra
  nop

hexasc:
  andi $v0, $a0, 0xF
  slti $t0, $v0, 0x0A
  beq $t0, $0, alfa
  nop
  addi $v0, $v0, 0x30
  jr $ra
  nop
  alfa:
    addi $v0, $v0, 0x37
  jr $ra
  nop

time2string:
  PUSH $ra
  PUSH $a0
  add $t2, $zero, $a0      #Return addr
  addi $t3, $zero, 12      #Shift counter
  addi $t4, $zero, 0xF000  #Mask
  addi $t5, $zero, 0       #Function counter
  loop:
    beq $t5, 2, skip
    nop
    and $t6, $a1, $t4  #Mask
    srlv $t6, $t6, $t3 #Shift to lowest 4 bits.
    subi $t3, $t3, 0x4 #Subtract 4 from shift counter.
    srl $t4, $t4, 0x4  #Shift mask 4 steps left.
    
    move $a0, $t6  #Move current num to argument.
    jal hexasc     #Convert to ASCII.
    nop
    sb $v0, 0($t2) #Save in memory
    
    addi $t5, $t5, 1 #Counter +1
    add $t2, $t2, 1  #Memory +1
    
    bne $t5, 5, loop
    nop
    beq $t5, 5, return
    nop
    skip:
      add $t2, $t2, 1
      addi $t5, $t5, 1
      j loop
      nop
  return:
    POP $a0
    POP $ra
    jr $ra
    nop
   
