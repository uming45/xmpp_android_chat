/*
 * Copyright 2016 Chummies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simpleandroidchat.chat.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By
 * Author   : Aung Thaw Aye
 * Email    : ata.aungthawaye@gmail.com
 * Date     : 29/10/16
 */

public enum MessageDirection {
    INCOMING(1),
    OUTGOING(2);

    /**
     * The Constant types.
     */
    private final static Map<Integer, MessageDirection> types = new HashMap<>();

    /**
     * The value.
     */
    private Integer value;

    static {
        for (MessageDirection accountStatus : MessageDirection.values()) {
            types.put(accountStatus.toValue(), accountStatus);
        }
    }

    /**
     * Instantiates a new account status.
     *
     * @param value the value
     */
    private MessageDirection(int value) {
        this.value = value;
    }

    /**
     * From value.
     *
     * @param value the value
     * @return the account status
     */
    public static MessageDirection fromValue(int value) {
        return types.get(value);
    }

    /**
     * To value.
     *
     * @return the int
     */
    public int toValue() {
        return this.value;
    }
    }
