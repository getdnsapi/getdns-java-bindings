/**
 * 
 */
package com.verisign.getdns.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

import org.junit.Test;

import com.verisign.getdns.GetDNSUtil;

/**
 * @author vsoni
 *
 */
@SuppressWarnings("unchecked")
public class GetDNSUtilTest {

	static HashMap<String, Object> info = new HashMap<String, Object>();
	static String filePath = "src/test/sampleInfo.txt";

	// Initialize the info object
	static {
		// create an ObjectInputStream for the file we created before
		FileInputStream fs = null;
		ObjectInputStream ois = null;
		try {
			fs = new FileInputStream(filePath);
			ois = new ObjectInputStream(fs);
			info = (HashMap<String, Object>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void getobjectTest() {
		assertNotNull(info);
		assertEquals("check class:  ", 1,
				Integer.parseInt(GetDNSUtil.getObject(info, "/replies_tree[0]/authority[0]/class").toString()));
		assertEquals("check type:  ", 2,
				Integer.parseInt(GetDNSUtil.getObject(info, "/replies_tree[0]/authority[0]/type").toString()));

		assertEquals("check rdata:  ", "mcvax.nlnet.nl.",
				GetDNSUtil.getObject(info, "/replies_tree[0]/authority[1]/rdata/rdata_raw").toString());

		assertEquals("check answer_type:  ", 800,
				Integer.parseInt(GetDNSUtil.getObject(info, "/replies_tree[0]/answer_type").toString()));

		assertEquals("check dnssec_status:  ", 400,
				Integer.parseInt(GetDNSUtil.getObject(info, "/replies_tree[0]/dnssec_status").toString()));
	}

	@Test
	public void getAsArrayListTest() {
		assertNotNull(info);
		assertNotNull(GetDNSUtil.getAsArrayList(info, "/replies_tree[0]/authority"));
	}

	@Test
	public void getasMapTest() {
		assertNotNull(info);
		assertNotNull(GetDNSUtil.getAsMap(info, "/replies_tree[0]/authority[0]/rdata"));
	}

	@Test
	public void getAsListOfMapTest() {
		assertNotNull(info);
		assertNotNull(GetDNSUtil.getAsListOfMap(info, "/replies_tree[0]/authority"));
	}

	/**
	 * ---------sample Response in sampleInfo.txt file-----
	 */
	// {just_address_answers=[
	// {
	// address_data=185.49.141.37,
	// address_type=IPv4
	// }],
	// answer_type=800,
	// status=900,
	// replies_full=[[B@248a9705],
	// replies_tree=[
	// {
	// authority=[
	// {
	// class=1,
	// ttl=450,
	// type=2,
	// rdata={rdata_raw=getdnsapi.net.,
	// nsdname=getdnsapi.net.
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=450,
	// type=2,
	// rdata={rdata_raw=mcvax.nlnet.nl.,
	// nsdname=mcvax.nlnet.nl.
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=450,
	// type=2,
	// rdata={rdata_raw=dicht.nlnetlabs.nl.,
	// nsdname=dicht.nlnetlabs.nl.
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=450,
	// type=46,
	// rdata={rdata_raw=[B@6c0dc01,
	// original_ttl=450,
	// signers_name=getdnsapi.net.,
	// type_covered=2,
	// labels=2,
	// key_tag=6273,
	// signature_expiration=1430027610,
	// signature_inception=1428217484,
	// signature=[B@5fabc91d,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// }],
	// answer_type=800,
	// additional=[
	// {
	// class=1,
	// ttl=450,
	// type=28,
	// rdata={ipv6_address=2a04:b900:0:100::37,
	// rdata_raw=[B@720bffd
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=450,
	// type=46,
	// rdata={rdata_raw=[B@771c7eb2,
	// original_ttl=450,
	// signers_name=getdnsapi.net.,
	// type_covered=28,
	// labels=2,
	// key_tag=6273,
	// signature_expiration=1430180042,
	// signature_inception=1428339884,
	// signature=[B@6fc21535,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// },
	// {udp_payload_size=4096,
	// do=1,
	// extended_rcode=0,
	// type=41,
	// z=0,
	// rdata={rdata_raw=,
	// options=[]
	// },
	// version=0
	// }],
	// answer=[
	// {
	// class=1,
	// ttl=450,
	// type=1,
	// rdata={ipv4_address=185.49.141.37,
	// rdata_raw=[B@2efe83e5
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=450,
	// type=46,
	// rdata={rdata_raw=[B@4785477d,
	// original_ttl=450,
	// signers_name=getdnsapi.net.,
	// type_covered=1,
	// labels=2,
	// key_tag=6273,
	// signature_expiration=1430051354,
	// signature_inception=1428203084,
	// signature=[B@7f188439,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// }],
	// dnssec_status=400,
	// question={qname=getdnsapi.net.,
	// qclass=1,
	// qtype=1
	// },
	// canonical_name=getdnsapi.net.,
	// header={arcount=2,
	// rcode=0,
	// rd=1,
	// opcode=0,
	// nscount=4,
	// id=0,
	// ra=1,
	// tc=0,
	// ad=1,
	// qdcount=1,
	// ancount=2,
	// aa=0,
	// z=0,
	// qr=1,
	// cd=0
	// }
	// }],
	// validation_chain=[
	// {
	// class=1,
	// ttl=172799,
	// type=48,
	// rdata={protocol=3,
	// rdata_raw=[B@2290fb5f,
	// public_key=[B@5c672bb3,
	// flags=256,
	// algorithm=8
	// },
	// name=.
	// },
	// {class=1,
	// ttl=172799,
	// type=48,
	// rdata={protocol=3,
	// rdata_raw=[B@79662429,
	// public_key=[B@20d03e03,
	// flags=256,
	// algorithm=8
	// },
	// name=.
	// },
	// {class=1,
	// ttl=172799,
	// type=48,
	// rdata={protocol=3,
	// rdata_raw=[B@479747c9,
	// public_key=[B@1cea01d7,
	// flags=257,
	// algorithm=8
	// },
	// name=.
	// },
	// {class=1,
	// ttl=172799,
	// type=46,
	// rdata={rdata_raw=[B@471ed915,
	// original_ttl=172800,
	// signers_name=.,
	// type_covered=48,
	// labels=0,
	// key_tag=19036,
	// signature_expiration=1429142399,
	// signature_inception=1427846400,
	// signature=[B@3a3e162d,
	// algorithm=8
	// },
	// name=.
	// },
	// {class=1,
	// ttl=3600,
	// type=48,
	// rdata={protocol=3,
	// rdata_raw=[B@4222023a,
	// public_key=[B@46c04fc1,
	// flags=257,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=3600,
	// type=48,
	// rdata={protocol=3,
	// rdata_raw=[B@7bb42c30,
	// public_key=[B@71c142c2,
	// flags=257,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=3600,
	// type=48,
	// rdata={protocol=3,
	// rdata_raw=[B@470608e6,
	// public_key=[B@5d325877,
	// flags=256,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=3600,
	// type=46,
	// rdata={rdata_raw=[B@144e1c6e,
	// original_ttl=3600,
	// signers_name=getdnsapi.net.,
	// type_covered=48,
	// labels=2,
	// key_tag=26203,
	// signature_expiration=1429354616,
	// signature_inception=1427533484,
	// signature=[B@142c842c,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=3600,
	// type=46,
	// rdata={rdata_raw=[B@76996f0c,
	// original_ttl=3600,
	// signers_name=getdnsapi.net.,
	// type_covered=48,
	// labels=2,
	// key_tag=64869,
	// signature_expiration=1429354616,
	// signature_inception=1427533484,
	// signature=[B@5349b9a8,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=86398,
	// type=43,
	// rdata={key_tag=26203,
	// digest=[B@d3bef50,
	// rdata_raw=[B@59dc73f9,
	// digest_type=1,
	// algorithm=7
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=86398,
	// type=46,
	// rdata={rdata_raw=[B@18420e86,
	// original_ttl=86400,
	// signers_name=net.,
	// type_covered=43,
	// labels=2,
	// key_tag=58004,
	// signature_expiration=1428814759,
	// signature_inception=1428205759,
	// signature=[B@18c78bdb,
	// algorithm=8
	// },
	// name=getdnsapi.net.
	// },
	// {class=1,
	// ttl=86400,
	// type=48,
	// rdata={protocol=3,
	// rdata_raw=[B@54aee392,
	// public_key=[B@25deb1ad,
	// flags=257,
	// algorithm=8
	// },
	// name=net.
	// },
	// {class=1,
	// ttl=86400,
	// type=48,
	// rdata={protocol=3,
	// rdata_raw=[B@3f62e847,
	// public_key=[B@767fadd3,
	// flags=256,
	// algorithm=8
	// },
	// name=net.
	// },
	// {class=1,
	// ttl=86400,
	// type=46,
	// rdata={rdata_raw=[B@386d346c,
	// original_ttl=86400,
	// signers_name=net.,
	// type_covered=48,
	// labels=1,
	// key_tag=35886,
	// signature_expiration=1428770337,
	// signature_inception=1428165237,
	// signature=[B@98cbb65,
	// algorithm=8
	// },
	// name=net.
	// },
	// {class=1,
	// ttl=86398,
	// type=43,
	// rdata={key_tag=35886,
	// digest=[B@7662e8c8,
	// rdata_raw=[B@57a0df30,
	// digest_type=2,
	// algorithm=8
	// },
	// name=net.
	// },
	// {class=1,
	// ttl=86398,
	// type=46,
	// rdata={rdata_raw=[B@29cf542,
	// original_ttl=86400,
	// signers_name=.,
	// type_covered=43,
	// labels=1,
	// key_tag=48613,
	// signature_expiration=1429246800,
	// signature_inception=1428379200,
	// signature=[B@ea9c165,
	// algorithm=8
	// },
	// name=net.
	// }],
	// canonical_name=getdnsapi.net.
	// }

}
