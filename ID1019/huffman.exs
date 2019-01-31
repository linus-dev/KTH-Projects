defmodule Hefner do
  def test(str) do
    freq = freq(str);
    tree = huffman(freq);
    table = encode_table(tree);
    encoding = encode(str, table);
    decoding = decode(encoding, table);
    decoding
  end
  
  def freq(l) do
    freq(l, []);
  end
  def freq([], freq) do
    freq;
  end
  
  def freq([char | rest], freq) do
    freq(rest, update(char, freq));
  end

  def update(char, []) do
    [{char, 1}];
  end
  
  def update(char, [{char, n} | tail]) do
    [{char, n + 1} | tail];
  end
  
  def update(char, [elem | tail]) do
    [elem | update(char, tail)];
  end
  def huffman(freq) do
    sorted = Enum.sort(freq, fn({_, x}, {_, y}) -> x < y end);
    huffman_tree(sorted);
  end

  def huffman_tree([{tree, _}]), do: tree
  def huffman_tree([{a, af}, {b, bf} | rest]) do
    huffman_tree(insert({{a, b}, af + bf}, rest));
  end

  def insert({a, af}, []) do
    [{a, af}];
  end

  # Call recursively until af is smaller than the next element.
  def insert({a, af}, l = [{_, bf} | _]) when af < bf do
    [{a, af} | l];
  end

  # Keep moving a forward until above pattern matches.
  def insert({a, af}, [{b, bf} | tail]) do
    [{b, bf} | insert({a, af}, tail)]
  end
  def encode_table(tree) do encode_table(tree, []) end
  def encode_table({a, b}, path) do
    # Go left.
    ap = encode_table(a, [0 | path]);
    # Go right.
    bp = encode_table(b, [1 | path]);
    ap ++ bp
  end
  def encode_table(leaf, path) do [{leaf, path}] end

  def encode([], _) do [] end
  def encode([char | tail], table) do
    {_, code} = List.keyfind(table, char, 0);
    code ++ encode(tail, table);
  end

  def decode([], _)  do
    []
  end

  def decode(seq, table) do
    {char, rest} = decode_char(seq, 1, table)
    [char | decode(rest, table)]
  end

  def decode_char(seq, n, table) do
    {code, rest} = Enum.split(seq, n)
    case List.keyfind(table, code, 1) do
      {char, _} -> {char, rest};
      nil -> decode_char(seq, n + 1, table);
    end
  end
  
end
