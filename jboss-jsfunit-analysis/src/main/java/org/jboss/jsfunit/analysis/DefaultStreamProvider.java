/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.jsfunit.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Dennis Byrne
 * @since 1.0
 */

public class DefaultStreamProvider implements StreamProvider {

	public InputStream getInputStream(String resourceName) {

		InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceName);

		if (stream == null)
			stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);

		File file;
		
		if(stream == null && ( file = new File(resourceName) ).exists() ) {
			
			if(file.isDirectory())
				throw new RuntimeException(resourceName + " exists but it needs to be a file.");
			
			try {
				stream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				throw new RuntimeException("File " + resourceName + " exists but it could not be read.");
			}
		}
		
		return stream;
	}

}
