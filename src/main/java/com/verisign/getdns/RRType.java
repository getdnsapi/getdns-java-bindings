package com.verisign.getdns;
import java.util.HashMap;


public class RRType {
        private static final HashMap<String,RRType> nameToRRTypeMap = new HashMap<String,RRType>();
        private static final HashMap<Integer,RRType> valueToRRTypeMap = new HashMap<Integer,RRType>();


        public static final RRType A = new RRType("A", 1),
                             NS = new RRType("NS", 2),
                             MD = new RRType("MD", 3),
                             MF = new RRType("MF", 4),
                             CNAME = new RRType("CNAME", 5),
                             SOA = new RRType("SOA", 6),
                             MB = new RRType("MB", 7),
                             MG = new RRType("MG", 8),
                             MR = new RRType("MR", 9),
                             NULL = new RRType("NULL", 10),
                             WKS = new RRType("WKS", 11),
                             PTR = new RRType("PTR", 12),
                             HINFO = new RRType("HINFO", 13),
                             MINFO = new RRType("MINFO", 14),
                             MX = new RRType("MX", 15),
                             TXT = new RRType("TXT", 16),
                             RP = new RRType("RP", 17),
                             AFSDB = new RRType("AFSDB", 18),
                             X25 = new RRType("", 19),
                             ISDN = new RRType("ISDN", 20),
                             RT = new RRType("RT", 21),
                             NSAP = new RRType("NSAP", 22),
                             SIG = new RRType("SIG", 24),
                             KEY = new RRType("KEY", 25),
                             PX = new RRType("PX", 26),
                             GPOS = new RRType("GPOS", 27),
                             AAAA = new RRType("AAAA", 28),
                             LOC = new RRType("LOC", 29),
                             NXT = new RRType("NXT", 30),
                             EID = new RRType("EID", 31),
                             NIMLOC = new RRType("NIMLOC", 32),
                             SRV = new RRType("SRV", 33),
                             ATMA = new RRType("ATMA", 34),
                             NAPTR = new RRType("NAPTR", 35),
                             KX = new RRType("KX", 36),
                             CERT = new RRType("CERT", 37),
                             A6 = new RRType("", 38),
                             DNAME = new RRType("DNAME", 39),
                             SINK = new RRType("SINK", 40),
                             OPT = new RRType("OPT", 41),
                             APL = new RRType("APL", 42),
                             DS = new RRType("DS", 43),
                             SSHFP = new RRType("SSHFP", 44),
                             IPSECKEY = new RRType("IPSECKEY", 45),
                             RRSIG = new RRType("RRSIG", 46),
                             NSEC = new RRType("NSEC", 47),
                             DNSKEY = new RRType("DNSKEY", 48),
                             DHCID = new RRType("DHCID", 49),
                             NSEC3 = new RRType("", 50),
                             NSEC3PARAM = new RRType("PARAM", 51),
                             TLSA = new RRType("TLSA", 52),
                             HIP = new RRType("HIP", 55),
                             NINFO = new RRType("NINFO", 56),
                             RKEY = new RRType("RKEY", 57),
                             TALINK = new RRType("TALINK", 58),
                             CDS = new RRType("CDS", 59),
                             CDNSKEY = new RRType("CDNSKEY", 60),
                             OPENPGPKEY = new RRType("OPENPGPKEY", 61),
                             SPF = new RRType("SPF", 99),
                             UINFO = new RRType("UINFO", 100),
                             UID = new RRType("UID", 101),
                             GID = new RRType("GID", 102),
                             UNSPEC = new RRType("UNSPEC", 103),
                             NID = new RRType("NID", 104),
                             L32 = new RRType("", 105),
                             L64 = new RRType("", 106),
                             LP = new RRType("LP", 107),
                             EUI48 = new RRType("", 108),
                             EUI64 = new RRType("", 109),
                             TKEY = new RRType("TKEY", 249),
                             TSIG = new RRType("TSIG", 250),
                             IXFR = new RRType("IXFR", 251),
                             AXFR = new RRType("AXFR", 252),
                             MAILB = new RRType("MAILB", 253),
                             MAILA = new RRType("MAILA", 254),
                             ANY = new RRType("ANY", 255),
                             URI = new RRType("URI", 256),
                             CAA = new RRType("CAA", 257),
                             TA = new RRType("TA", 32768),
                             DLV = new RRType("DLV", 32769);

        static{
            for(int i=0;i<65535;i++) {
                if(!valueToRRTypeMap.containsKey(i))
                    new RRType("TYPE"+i, i);
            }
        }
	
        private final String name;
	private final int value;

	private RRType(String name, int value) {
                this.name = name;
		this.value = value;
                nameToRRTypeMap.put(name, this);
                valueToRRTypeMap.put(value, this);
	}
        
        public static final RRType valueOf(int value){
            if(valueToRRTypeMap.containsKey(value))
                return valueToRRTypeMap.get(value);
            else
                throw new IllegalArgumentException("Invalid RRType value passed");
        }

        public static final RRType valueOf(String name){
            if(nameToRRTypeMap.containsKey(name))
                return nameToRRTypeMap.get(name);
            else
                throw new IllegalArgumentException("Invalid RRType name passed");
        }
	
    public int getValue() { return value; }
}
