defmodule Sort do

  def isort(l) do isort(l, []) end
  def isort([], sorted) do sorted end
  def isort([head | tail], sorted) do
    isort(tail, insert(head, sorted))
  end

  def insert(x, []) do [x] end
  def insert(x, [head | tail]) when x < head do
    [x, head | tail];
  end
  def insert(x, [head | tail]) do
    [head | insert(x, tail)]
  end
  
  def msort([]) do [] end
  def msort([x]) do [x] end
  def msort(l) do
    {l1, l2} = split(l, [], []); 
    merge(msort(l1), msort(l2));
  end

  def split([], l1, l2) do {l1, l2} end
  def split([x | tail], l1, l2) do
    split(tail, [x | l2], l1) 
  end

  def merge([], l2) do l2 end
  def merge(l1, []) do l1 end
  def merge([x1 | l1], [x2 | _] = l2) when x1 < x2 do
    [x1 | merge(l1, l2)];
  end
  def merge(l1, [x2 | l2]) do
    [x2 | merge(l1, l2)];
  end

  def qsplit(_, [], small, large) do {small, large} end
  def qsplit(p, [h | t], small, large) do
    if h <= p do
      qsplit(p, t, [h | small], large);
    else
      qsplit(p, t, small, [h | large]);
    end
  end 
end

  
