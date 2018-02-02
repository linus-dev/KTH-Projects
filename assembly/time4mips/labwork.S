  # labwork.S
  # Written 2015-2017 by F Lundevall
  # Skeleton file for IS1200/IS1500 lab 1.
  # The macros PUSH and POP are in the public domain.
  # Please add your own code at the end of the file.

  #
  # Please keep the two macros PUSH and POP unchanged
  #
.macro	PUSH reg
	addi	$sp,$sp,-4
	sw	\reg,0($sp)
.endm

.macro	POP reg
	lw	\reg,0($sp)
	addi	$sp,$sp,4
.endm
  #
  # Please add your own code below this line
  #
.global delay
.global hexasc
.global time2string
.data
.align 2
.text

delay:
  PUSH $a0
  ble $a0, $zero, delay_return
  nop
  loop_outer:
    addi $t0, $zero, 7000 
    loop_inner:
      addi $t0, $t0, -1
      bne $t0, 0, loop_inner
      nop
    addi $a0, $a0, -1
    bne $a0, 0, loop_outer
    nop
  delay_return:
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
  addi $t2, $zero, 0x3a
  sb $t2, 2($a0)
  add $t2, $zero, $a0      #Return addr
  addi $t3, $zero, 12      #Shift counter
  addi $t4, $zero, 0xF000  #Mask
  addi $t5, $zero, 0       #Function counter
  andi $t7, $a1,   0xFFFF  #Current time (NEW SHITTY CODE)
  beq $t7, 0x0, HOUR
  loop:
    beq $t5, 2, skip
    nop
    and $t6, $a1, $t4  #Mask
    srlv $t6, $t6, $t3 #Shift to lowest 4 bits.
    addi $t3, $t3, -4 #Subtract 4 from shift counter.
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
    # Really shitty code.
    HOUR:
      addi $a3, $zero, 0x48
      sb $a3, 0($t2)
      addi $a3, $zero, 0x4f
      sb $a3, 1($t2)
      addi $a3, $zero, 0x55
      sb $a3, 2($t2)
      addi $a3, $zero, 0x52
      sb $a3, 3($t2)
      addi $a3, $zero, 0x20
      sb $a3, 4($t2)
      j return
      nop
  return:
    POP $a0
    POP $ra
    jr $ra
    nop
   
