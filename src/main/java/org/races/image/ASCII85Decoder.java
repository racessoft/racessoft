package org.races.image;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import javax.imageio.IIOException;

/**
 * decode ASCII85 text into a byte array.

 * @author Mike Wessler
 */
public class ASCII85Decoder
{
	private ByteBuffer m_oBuffer;

	/**
	* initialize the decoder with byte buffer in ASCII85 format
	*/
	private ASCII85Decoder(ByteBuffer buf)
	{
		m_oBuffer = buf;
	}

	/**
	 * get the next character from the input.
	 * @return the next character, or -1 if at end of stream
	 */
	private int nextChar()
	{
		// skip whitespace
		// returns next character, or -1 if end of stream
		while(m_oBuffer.remaining() > 0)
		{
			char c = (char) m_oBuffer.get();

			if(!Character.isWhitespace(c))
			{
				return c;
			}
		}

		// EOF reached
		return -1;
	}

	/**
	 * decode the next five ASCII85 characters into up to four decoded
	 * bytes.  Return false when finished, or true otherwise.
	 *
	 * @param baos the ByteArrayOutputStream to write output to, set to the
	 *        correct position
	 * @return false when finished, or true otherwise.
	 */
	private boolean decode5(ByteArrayOutputStream baos) throws IIOException
	{
		// stream ends in ~>
		int[] five = new int[5];
		int i;

		for (i = 0; i < 5; i++)
		{
			five[i] = nextChar();

			if(five[i] == '~')
			{
				if(nextChar() == '>')
				{
					break;
				}
				else
				{
					throw new IIOException("Bad character in ASCII85Decode: not ~>");
				}
			}
			else if(five[i] >= '!' && five[i] <= 'u')
			{
				five[i] -= '!';
			}
			else if(five[i] == 'z')
			{
				if(i == 0)
				{
					five[i] = 0;
					i = 4;
				}
				else
				{
					throw new IIOException("Inappropriate 'z' in ASCII85Decode");
				}
			}
			else
			{
				throw new IIOException("Bad character in ASCII85Decode: " + five[i] + " (" + (char) five[i] + ")");
			}
		}

		if(i > 0)
		{
			i -= 1;
		}

		int value = five[0] * 85 * 85 * 85 * 85 + five[1] * 85 * 85 * 85 + five[2] * 85 * 85 + five[3] * 85 + five[4];

		for(int j = 0; j < i; j++)
		{
			int shift = 8 * (3 - j);
			baos.write((byte)((value >> shift) & 0xff));
		}

		return (i == 4);
	}

	/**
	 * decode the bytes
	 * @return the decoded bytes
	 */
	private ByteBuffer decode() throws IIOException
	{
		// start from the beginning of the data
		m_oBuffer.rewind();
		// allocate the output buffer
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// decode the bytes

		while (decode5(baos))
		{

		}

		return ByteBuffer.wrap(baos.toByteArray());
	}

	/**
	 * decode an array of bytes in ASCII85 format.
	 * <p>
	 * In ASCII85 format, every 5 characters represents 4 decoded
	 * bytes in base 85.  The entire stream can contain whitespace,
	 * and ends in the characters '~&gt;'.
	 *
	 * @param buf the encoded ASCII85 characters in a byte buffer
	 * @return the decoded bytes
	 */
	public static ByteBuffer decode(ByteBuffer buf) throws IIOException
	{
		ASCII85Decoder me = new ASCII85Decoder(buf);
		
		return me.decode();
	}
}