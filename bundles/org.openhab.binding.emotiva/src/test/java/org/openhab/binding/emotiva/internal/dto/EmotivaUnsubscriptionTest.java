/**
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.emotiva.internal.dto;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openhab.binding.emotiva.internal.EmotivaBindingConstants.CHANNEL_TUNER_RDS;

import javax.xml.bind.JAXBException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.jupiter.api.Test;
import org.openhab.binding.emotiva.internal.AbstractDTOTestBase;
import org.openhab.binding.emotiva.internal.protocol.EmotivaControlCommands;
import org.openhab.binding.emotiva.internal.protocol.EmotivaSubscriptionTags;

/**
 * Unit tests for EmotivaUnsubscribe requests.
 *
 * @author Espen Fossen - Initial contribution
 */
@NonNullByDefault
class EmotivaUnsubscriptionTest extends AbstractDTOTestBase {

    public EmotivaUnsubscriptionTest() throws JAXBException {
    }

    @Test
    void marshalFromChannelUID() {
        var subscriptionChannel = EmotivaSubscriptionTags.fromChannelUID(CHANNEL_TUNER_RDS);
        var emotivaSubscriptionRequest = new EmotivaUnsubscribeDTO(subscriptionChannel);
        String xmlString = xmlUtils.marshallJAXBElementObjects(emotivaSubscriptionRequest);

        assertThat(xmlString, containsString("<emotivaUnsubscribe>"));
        assertThat(xmlString, containsString("<tuner_RDS />"));
        assertThat(xmlString, containsString("</emotivaUnsubscribe>"));
    }

    @Test
    void marshallWithTwoUnsubscriptions() {
        var command1 = new EmotivaCommandDTO(EmotivaControlCommands.volume);
        var dto = new EmotivaUnsubscribeDTO(command1);
        String xmlString = xmlUtils.marshallJAXBElementObjects(dto);

        assertThat(xmlString, containsString("<emotivaUnsubscribe>"));
        assertThat(xmlString, containsString("<volume />"));
        assertThat(xmlString, containsString("</emotivaUnsubscribe>"));
        assertThat(xmlString, not(containsString("<volume>")));
    }
}
