export default {
  App: {
    components: {
      tooltip: '[[{{keys.ctrl}}]] + [[1]] Switch to Components',
    },
    events: {
      tooltip: '[[{{keys.ctrl}}]] + [[3]] Switch to Events',
    },
    refresh: {
      tooltip: '[[{{keys.ctrl}}]] + [[{{keys.alt}}]] + [[R]] Force Refresh',
    },
    routing: {
      tooltip: '[[{{keys.ctrl}}]] + [[4]] Switch to Routing',
    },
    perf: {
      tooltip: '[[{{keys.ctrl}}]] + [[5]] Switch to Performance',
    },
    settings: {
      tooltip: '[[{{keys.ctrl}}]] + [[6]] Switch to Settings',
    },
    vuex: {
      tooltip: '[[{{keys.ctrl}}]] + [[2]] Switch to Vuex',
    },
  },
  StateInspector: {
    dataType: {
      tooltip: '[[{{keys.shift}}]] + <<mouse>>: Expand/Collapse All<br><div class="max-w-[200px] opacity-70">Using this shortcut will override the default toggled state and save it for next time.</div>',
    },
    filter: {
      tooltip: '[[{{keys.alt}}]] + [[D]] Filter state by name',
    },
  },
  DataField: {
    edit: {
      cancel: {
        tooltip: '[[{{keys.esc}}]] Cancel',
      },
      submit: {
        tooltip: '[[{{keys.enter}}]] Submit change',
      },
    },
    contextMenu: {
      copyValue: 'Copy Value',
      copyPath: 'Copy Path',
    },
    quickEdit: {
      number: {
        tooltip: `Quick Edit<br><br>
        [[{{keys.ctrl}}]] + <<mouse>>: {{operator}}5<br>
        [[{{keys.shift}}]] + <<mouse>>: {{operator}}10<br>
        [[{{keys.alt}}]] + <<mouse>>: {{operator}}100`,
      },
    },
  },
  ComponentTree: {
    select: {
      tooltip: '[[{{keys.alt}}]] + [[S]] Select component in the page',
    },
    filter: {
      tooltip: '[[{{keys.alt}}]] + [[F]] Filter components by name',
    },
    refresh: {
      tooltip: '[[{{keys.ctrl}}]] + [[{{keys.alt}}]] + [[R]] Force refresh',
    },
  },
  ComponentInstance: {
    consoleId: {
      tooltip: 'Available as <mono>{{id}}</mono> in the console.',
    },
  },
  ComponentInspector: {
    openInEditor: {
      tooltip: 'Open <mono><<insert_drive_file>>{{file}}</mono> in editor',
    },
  },
  EventsHistory: {
    filter: {
      tooltip: '[[{{keys.ctrl}}]] + [[F]] To filter on components, type <input><<search>> &lt;MyComponent&gt;</input> or just <input><<search>> &lt;mycomp</input>.',
    },
    clear: {
      tooltip: '[[{{keys.ctrl}}]] + [[{{keys.del}}]] Clear Log',
    },
    startRecording: {
      tooltip: '[[R]] Start recording',
    },
    stopRecording: {
      tooltip: '[[R]] Stop recording',
    },
  },
  VuexHistory: {
    filter: {
      tooltip: '[[{{keys.ctrl}}]] + [[F]] Filter mutations',
    },
    commitAll: {
      tooltip: '[[{{keys.ctrl}}]] + [[{{keys.enter}}]] Commit all',
    },
    revertAll: {
      tooltip: '[[{{keys.ctrl}}]] + [[{{keys.del}}]] Revert all',
    },
    startRecording: {
      tooltip: '[[R]] Start recording',
    },
    stopRecording: {
      tooltip: '[[R]] Stop recording',
    },
  },
}
