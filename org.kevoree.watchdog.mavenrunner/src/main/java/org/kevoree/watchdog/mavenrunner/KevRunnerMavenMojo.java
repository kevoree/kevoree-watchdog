/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kevoree.watchdog.mavenrunner;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.kevoree.watchdog.Runner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * User: Grégory Nain - gregory.nain@gmail.com
 * Date: 15/03/12
 * Time: 12:58
 *
 * @author Grégory Nain
 * @version 1.0
 * @goal run
 * @phase install
 * @requiresDependencyResolution compile+runtime
 */
public class KevRunnerMavenMojo extends AbstractMojo {

	/**
	 * @parameter default-value="${project.basedir}/src/main/kevs/main.kevs"
	 */
	private File bootstrapModel;

	/**
	 * @parameter default-value="RELEASE"
	 */
	private String kevoreeVersion;

	/**
	 * The maven project.
	 *
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;


	public void execute () throws MojoExecutionException {

		String[] args = new String[2];
        args[0] = kevoreeVersion;
        args[1] = bootstrapModel.getAbsolutePath();

        try {

            Runner.main(args);

            Thread.currentThread().join();


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


}
