defmodule Lists do
  def nth(0, [head | _]) do head end
  def nth(n, [_ | tail]) do
    nth(n-1, tail);
  end
  
  def len([]) do 0 end
  def len([_ | tail]) do
    1 + len(tail); 
  end

  def sum([]) do 0 end
  def sum([head | tail]) do
    head + sum(tail);
  end
  
  def duplicate([]) do [] end
  def duplicate([head | tail]) do
    [head, head | duplicate(tail)];
  end
  
  def add(x, []) do [x] end
  def add(x, [x | tail]) do
    [x | tail];
  end
  def add(x, [head | tail]) do
    [head | add(x, tail)];
  end
  
  def remove(_, []) do [] end
  def remove(x, [x | tail]) do
    remove(x, tail);
  end
  def remove(x, [head | tail]) do
    [head | remove(x, tail)];
  end

  def unique([]) do [] end;
  def unique([head | tail]) do
    [head | unique(remove(head, tail))];
  end
  
  def pack([]) do [] end
  def pack([head | tail]) do
    {all, rest} = match(head, tail, [head], []);
    [all | pack(rest)];
  end

  def match(x, [], all, rest) do {all, rest} end
  def match(x, [x | tail], all, rest) do
    match(x, tail, [x | all], rest);
  end
  def match(x, [y | tail], all, rest) do
    match(x, tail, all, [y | rest]);
  end
  def reverse(list) do reverse(list, []) end 
  def reverse([], rev) do rev end
  def reverse([head | tail], rev) do
    reverse(tail, [head | rev]); 
  end
end
