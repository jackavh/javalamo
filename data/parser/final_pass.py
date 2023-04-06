import os


input_path = "data/WikiData/AA/wiki_00"
output_path = "data/WikiData/AA/wiki_00_clean"


def remove_arrows(line):
    """
    processes a line of file as a string
    """

    start = -1
    end = -1
    for i, char in enumerate(line):
        if (char is '<') and (start == -1):
            start = i
        if char is '>':
            end = i+1
    
    # return (line[:start] + line[end:]).strip()
    return line[:start] + line[end:]


def read_in_chunks(file, block_size=8192):
    """
    Lazy function (generator) to read a file piece by piece.
    Default chunk size: 8k.
    """
    while True:
        data = file.read(block_size)
        if not data:
            break
        yield data


def open_output(idx):
    return open(output_path + '_{}'.format(idx), 'w')


CHUNKS_SIZE = 128
chunk_counter = 0
output_counter = 0
with open(input_path, 'r') as input_file:
    input_size = os.path.getsize(input_path)
    output_file = open_output(output_counter)
    for chunk in read_in_chunks(input_file):
        chunk_counter += 1
        if chunk_counter % CHUNKS_SIZE == 0:
            print('{:,}/{:,} bytes processed | Chunk {}'.format(
                input_file.tell(), input_size, chunk_counter))
            output_file.close()
            output_counter += 1
            output_file = open_output(output_counter)
        for line in chunk.splitlines():
            if line is not '\n':
                output_file.write(remove_arrows(line))
                