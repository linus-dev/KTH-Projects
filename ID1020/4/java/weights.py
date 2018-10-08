f = open('contiguous-usa.dat', 'r')
fl = f.readlines()
newFile = open('data_weighted.dat', 'w')
i = 1 
for line in fl:
  newFile.write(line.rstrip() + " " + str(i) + '\n')
  i += 1
