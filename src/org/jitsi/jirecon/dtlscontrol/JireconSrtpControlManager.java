/*
 * Jirecon, the Jitsi recorder container.
 * 
 * Distributable under LGPL license. See terms of license at gnu.org.
 */

package org.jitsi.jirecon.dtlscontrol;

import org.jitsi.service.neomedia.*;

public interface JireconSrtpControlManager
{
    public void addRemoteFingerprint(MediaType mediaType, String fingerprint);

    public String getLocalFingerprint(MediaType mediaType);

    public String getLocalFingerprintHashFunction(MediaType mediaType);

    public SrtpControl getSrtpControl(MediaType mediaType);
}