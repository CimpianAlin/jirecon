/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.service.neomedia.recording;

import org.jitsi.service.neomedia.*;
import org.json.simple.*;

/**
 * Represents an event related to media recording, such as a new SSRC starting
 * to be recorded.
 *
 * @author Boris Grozev
 * @author Vladimir Marinov
 */
public class RecorderEvent
{
    private Type type = Type.OTHER;
    private long instant = -1;
    private long ssrc = -1;
    private long audioSsrc = -1;
    private long rtpTimestamp = -1;
    private double ntpTime = -1.0;
    private long duration = -1;
    private AspectRatio aspectRatio = AspectRatio.ASPECT_RATIO_UNKNOWN;
    private String filename;
    private MediaType mediaType = null;
    private String participantName = null;
    private String participantDescription = null;
    private boolean disableOtherVideosOnTop = false;

    public RecorderEvent()
    {
    }

    public RecorderEvent(JSONObject json)
    {
        Object o = json.get("type");
        if (o != null)
            type = Type.parseString(o.toString());

        o = json.get("instant");
        if (o != null &&
                (o instanceof Long || o instanceof Integer))
            instant = (Long)o;

        o = json.get("ssrc");
        if (o != null &&
                (o instanceof Long || o instanceof Integer))
            ssrc = (Long)o;

        o = json.get("audioSsrc");
        if (o != null &&
                (o instanceof Long || o instanceof Integer))
            audioSsrc = (Long)o;

        o = json.get("ntpTime");
        if (o != null &&
                (o instanceof Long || o instanceof Integer))
            ntpTime = (Long) o;

        o = json.get("duration");
        if (o != null &&
                (o instanceof Long || o instanceof Integer))
            duration = (Long) o;

        o = json.get("aspectRatio");
        if (o != null)
            aspectRatio = AspectRatio.parseString(o.toString());

        o = json.get("filename");
        if (o != null)
            filename = o.toString();

        o = json.get("participantName");
        if (o != null && o instanceof String)
            participantName = (String) o;

        o = json.get("participantDescription");
        if (o != null && o instanceof String)
            participantDescription = (String) o;

        o = json.get("mediaType");
        if (o != null)
        {
            try
            {
                mediaType = MediaType.parseString(o.toString());
            }
            catch (IllegalArgumentException iae)
            {
                //complain?
            }
        }
        
        o = json.get("disableOtherVideosOnTop");
        if (o != null)
        {
            if (o instanceof Boolean)
                disableOtherVideosOnTop = (boolean) disableOtherVideosOnTop;
            else if (o instanceof String)
                disableOtherVideosOnTop = Boolean.valueOf((String) o);
        }
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public long getInstant()
    {
        return instant;
    }

    public void setInstant(long instant)
    {
        this.instant = instant;
    }

    public long getRtpTimestamp()
    {
        return rtpTimestamp;
    }

    public void setRtpTimestamp(long rtpTimestamp)
    {
        this.rtpTimestamp = rtpTimestamp;
    }

    public long getSsrc()
    {
        return ssrc;
    }

    public void setSsrc(long ssrc)
    {
        this.ssrc = ssrc;
    }

    public long getAudioSsrc()
    {
        return audioSsrc;
    }

    public void setAudioSsrc(long audioSsrc)
    {
        this.audioSsrc = audioSsrc;
    }

    public AspectRatio getAspectRatio()
    {
        return aspectRatio;
    }

    public void setAspectRatio(AspectRatio aspectRatio)
    {
        this.aspectRatio = aspectRatio;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public MediaType getMediaType()
    {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
    }

    public long getDuration()
    {
        return duration;
    }

    public void setDuration(long duration)
    {
        this.duration = duration;
    }

    public String getParticipantName()
    {
        return participantName;
    }

    public void setParticipantName(String participantName)
    {
        this.participantName = participantName;
    }

    public String getParticipantDescription()
    {
        return participantDescription;
    }

    public void setParticipantDescription(String participantDescription)
    {
        this.participantDescription = participantDescription;
    }
    
    public boolean getDisableOtherVideosOnTop()
    {
        return disableOtherVideosOnTop;
    }
    
    public void setDisableOtherVideosOnTop(boolean disableOtherVideosOnTop)
    {
        this.disableOtherVideosOnTop = disableOtherVideosOnTop ;
    }

    public double getNtpTime()
    {
        return ntpTime;
    }

    public void setNtpTime(double ntpTime)
    {
        this.ntpTime = ntpTime;
    }

    public String toString()
    {
        return "RecorderEvent: " + getType().toString() + " @" + getInstant()
                + "(" + getMediaType() + ")";
    }

    public enum Type
    {
        /**
         * Indicates the start of a recording.
         */
        RECORDING_STARTED("RECORDING_STARTED"),

        /**
         * Indicates the end of a recording.
         */
        RECORDING_ENDED("RECORDING_ENDED"),

        /**
         * Indicates that the active speaker has changed. The 'audioSsrc'
         * field indicates the SSRC of the audio stream which is now considered
         * active, and the 'ssrc' field contains the SSRC of a video stream
         * associated with the now active audio stream.
         */
        SPEAKER_CHANGED("SPEAKER_CHANGED"),

        /**
         * Indicates that a new stream was added. This is different than
         * RECORDING_STARTED, because a new stream might be saved to an existing
         * recording (for example, a new audio stream might be added to a mix)
         */
        STREAM_ADDED("STREAM_ADDED"),

        /**
         * Default value.
         */
        OTHER("OTHER");

        private String name;

        private Type(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return name;
        }

        public static Type parseString(String str)
        {
            for (Type type : Type.values())
                if (type.toString().equals(str))
                    return type;
            return OTHER;
        }
    }

    public enum AspectRatio
    {
        ASPECT_RATIO_16_9("16_9", 16./9),
        ASPECT_RATIO_4_3("4_3", 4./3),
        ASPECT_RATIO_UNKNOWN("UNKNOWN", 1.);

        public double scaleFactor;
        private String stringValue;

        private AspectRatio(String stringValue, double scaleFactor)
        {
            this.scaleFactor = scaleFactor;
            this.stringValue = stringValue;
        }

        @Override
        public String toString()
        {
            return stringValue;
        }

        public static AspectRatio parseString(String str)
        {
            for (AspectRatio aspectRatio : AspectRatio.values())
                if (aspectRatio.toString().equals(str))
                    return aspectRatio;
            return ASPECT_RATIO_UNKNOWN;
        }
    }

}
