/**
 *   Copyright 2012-2015 TIKAL-TECHNOLOGY
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.tikal.tallerWeb.control;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 
 * @author Nekorp
 */
public class MainClass {

    private static final Logger LOGGER = Logger.getLogger(MainClass.class);

    public static void main(String arg[]) {
        try {
            ApplicationContext mini = new ClassPathXmlApplicationContext(
                    "spring/mini-appContext.xml");
            StartupController startupApp = mini.getBean(StartupController.class);
            startupApp.startup();
        } catch (Exception e) {
            MainClass.LOGGER.error("No se logro inicializar la aplicacion", e);
            System.exit(1);
        }
    }
}
