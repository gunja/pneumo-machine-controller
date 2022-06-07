from pymodbus.client.sync import ModbusTcpClient

client = ModbusTcpClient('192.168.1.1')

client.read_holding_registers(387, 23)
client.write_registers(387, [ 97 + 256 * 98, 99 + 256 * 100, 101 + 256 * 102, 103+256 * 0 ])

v = client.read_holding_registers(387, 23)
for i in range(23):
    v = result.getRegister(i)
    print(chr(v & 0xFF), chr((v>>8) & 0xFF))

client.write_registers(49 + 2 * 10, [ ord('H') + 256 * ord('e'), ord('e') + 256 * ord('l'), ord('l') + 256 * ord('o'), ord(' ') + 256 * ord('K'), ord('i') + 256 * 0, 0])
